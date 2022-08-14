import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReseauxSociauxFormService } from './reseaux-sociaux-form.service';
import { ReseauxSociauxService } from '../service/reseaux-sociaux.service';
import { IReseauxSociaux } from '../reseaux-sociaux.model';

import { ReseauxSociauxUpdateComponent } from './reseaux-sociaux-update.component';

describe('ReseauxSociaux Management Update Component', () => {
  let comp: ReseauxSociauxUpdateComponent;
  let fixture: ComponentFixture<ReseauxSociauxUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reseauxSociauxFormService: ReseauxSociauxFormService;
  let reseauxSociauxService: ReseauxSociauxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReseauxSociauxUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReseauxSociauxUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReseauxSociauxUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reseauxSociauxFormService = TestBed.inject(ReseauxSociauxFormService);
    reseauxSociauxService = TestBed.inject(ReseauxSociauxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reseauxSociaux: IReseauxSociaux = { id: 456 };

      activatedRoute.data = of({ reseauxSociaux });
      comp.ngOnInit();

      expect(comp.reseauxSociaux).toEqual(reseauxSociaux);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReseauxSociaux>>();
      const reseauxSociaux = { id: 123 };
      jest.spyOn(reseauxSociauxFormService, 'getReseauxSociaux').mockReturnValue(reseauxSociaux);
      jest.spyOn(reseauxSociauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reseauxSociaux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reseauxSociaux }));
      saveSubject.complete();

      // THEN
      expect(reseauxSociauxFormService.getReseauxSociaux).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reseauxSociauxService.update).toHaveBeenCalledWith(expect.objectContaining(reseauxSociaux));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReseauxSociaux>>();
      const reseauxSociaux = { id: 123 };
      jest.spyOn(reseauxSociauxFormService, 'getReseauxSociaux').mockReturnValue({ id: null });
      jest.spyOn(reseauxSociauxService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reseauxSociaux: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reseauxSociaux }));
      saveSubject.complete();

      // THEN
      expect(reseauxSociauxFormService.getReseauxSociaux).toHaveBeenCalled();
      expect(reseauxSociauxService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReseauxSociaux>>();
      const reseauxSociaux = { id: 123 };
      jest.spyOn(reseauxSociauxService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reseauxSociaux });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reseauxSociauxService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

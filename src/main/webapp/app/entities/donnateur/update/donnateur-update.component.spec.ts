import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonnateurFormService } from './donnateur-form.service';
import { DonnateurService } from '../service/donnateur.service';
import { IDonnateur } from '../donnateur.model';

import { DonnateurUpdateComponent } from './donnateur-update.component';

describe('Donnateur Management Update Component', () => {
  let comp: DonnateurUpdateComponent;
  let fixture: ComponentFixture<DonnateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donnateurFormService: DonnateurFormService;
  let donnateurService: DonnateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonnateurUpdateComponent],
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
      .overrideTemplate(DonnateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonnateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donnateurFormService = TestBed.inject(DonnateurFormService);
    donnateurService = TestBed.inject(DonnateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const donnateur: IDonnateur = { id: 456 };

      activatedRoute.data = of({ donnateur });
      comp.ngOnInit();

      expect(comp.donnateur).toEqual(donnateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonnateur>>();
      const donnateur = { id: 123 };
      jest.spyOn(donnateurFormService, 'getDonnateur').mockReturnValue(donnateur);
      jest.spyOn(donnateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donnateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donnateur }));
      saveSubject.complete();

      // THEN
      expect(donnateurFormService.getDonnateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(donnateurService.update).toHaveBeenCalledWith(expect.objectContaining(donnateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonnateur>>();
      const donnateur = { id: 123 };
      jest.spyOn(donnateurFormService, 'getDonnateur').mockReturnValue({ id: null });
      jest.spyOn(donnateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donnateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donnateur }));
      saveSubject.complete();

      // THEN
      expect(donnateurFormService.getDonnateur).toHaveBeenCalled();
      expect(donnateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonnateur>>();
      const donnateur = { id: 123 };
      jest.spyOn(donnateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donnateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donnateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

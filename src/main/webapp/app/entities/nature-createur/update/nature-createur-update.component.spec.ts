import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NatureCreateurFormService } from './nature-createur-form.service';
import { NatureCreateurService } from '../service/nature-createur.service';
import { INatureCreateur } from '../nature-createur.model';

import { NatureCreateurUpdateComponent } from './nature-createur-update.component';

describe('NatureCreateur Management Update Component', () => {
  let comp: NatureCreateurUpdateComponent;
  let fixture: ComponentFixture<NatureCreateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureCreateurFormService: NatureCreateurFormService;
  let natureCreateurService: NatureCreateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NatureCreateurUpdateComponent],
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
      .overrideTemplate(NatureCreateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureCreateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureCreateurFormService = TestBed.inject(NatureCreateurFormService);
    natureCreateurService = TestBed.inject(NatureCreateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureCreateur: INatureCreateur = { id: 456 };

      activatedRoute.data = of({ natureCreateur });
      comp.ngOnInit();

      expect(comp.natureCreateur).toEqual(natureCreateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureCreateur>>();
      const natureCreateur = { id: 123 };
      jest.spyOn(natureCreateurFormService, 'getNatureCreateur').mockReturnValue(natureCreateur);
      jest.spyOn(natureCreateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureCreateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureCreateur }));
      saveSubject.complete();

      // THEN
      expect(natureCreateurFormService.getNatureCreateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureCreateurService.update).toHaveBeenCalledWith(expect.objectContaining(natureCreateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureCreateur>>();
      const natureCreateur = { id: 123 };
      jest.spyOn(natureCreateurFormService, 'getNatureCreateur').mockReturnValue({ id: null });
      jest.spyOn(natureCreateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureCreateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureCreateur }));
      saveSubject.complete();

      // THEN
      expect(natureCreateurFormService.getNatureCreateur).toHaveBeenCalled();
      expect(natureCreateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureCreateur>>();
      const natureCreateur = { id: 123 };
      jest.spyOn(natureCreateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureCreateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureCreateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

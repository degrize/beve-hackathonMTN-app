import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InspirationFormService } from './inspiration-form.service';
import { InspirationService } from '../service/inspiration.service';
import { IInspiration } from '../inspiration.model';

import { InspirationUpdateComponent } from './inspiration-update.component';

describe('Inspiration Management Update Component', () => {
  let comp: InspirationUpdateComponent;
  let fixture: ComponentFixture<InspirationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inspirationFormService: InspirationFormService;
  let inspirationService: InspirationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InspirationUpdateComponent],
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
      .overrideTemplate(InspirationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InspirationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inspirationFormService = TestBed.inject(InspirationFormService);
    inspirationService = TestBed.inject(InspirationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const inspiration: IInspiration = { id: 456 };

      activatedRoute.data = of({ inspiration });
      comp.ngOnInit();

      expect(comp.inspiration).toEqual(inspiration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInspiration>>();
      const inspiration = { id: 123 };
      jest.spyOn(inspirationFormService, 'getInspiration').mockReturnValue(inspiration);
      jest.spyOn(inspirationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspiration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspiration }));
      saveSubject.complete();

      // THEN
      expect(inspirationFormService.getInspiration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(inspirationService.update).toHaveBeenCalledWith(expect.objectContaining(inspiration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInspiration>>();
      const inspiration = { id: 123 };
      jest.spyOn(inspirationFormService, 'getInspiration').mockReturnValue({ id: null });
      jest.spyOn(inspirationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspiration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspiration }));
      saveSubject.complete();

      // THEN
      expect(inspirationFormService.getInspiration).toHaveBeenCalled();
      expect(inspirationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInspiration>>();
      const inspiration = { id: 123 };
      jest.spyOn(inspirationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspiration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inspirationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CreateurAfricainFormService } from './createur-africain-form.service';
import { CreateurAfricainService } from '../service/createur-africain.service';
import { ICreateurAfricain } from '../createur-africain.model';

import { CreateurAfricainUpdateComponent } from './createur-africain-update.component';

describe('CreateurAfricain Management Update Component', () => {
  let comp: CreateurAfricainUpdateComponent;
  let fixture: ComponentFixture<CreateurAfricainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let createurAfricainFormService: CreateurAfricainFormService;
  let createurAfricainService: CreateurAfricainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CreateurAfricainUpdateComponent],
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
      .overrideTemplate(CreateurAfricainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CreateurAfricainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    createurAfricainFormService = TestBed.inject(CreateurAfricainFormService);
    createurAfricainService = TestBed.inject(CreateurAfricainService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const createurAfricain: ICreateurAfricain = { id: 456 };

      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      expect(comp.createurAfricain).toEqual(createurAfricain);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreateurAfricain>>();
      const createurAfricain = { id: 123 };
      jest.spyOn(createurAfricainFormService, 'getCreateurAfricain').mockReturnValue(createurAfricain);
      jest.spyOn(createurAfricainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: createurAfricain }));
      saveSubject.complete();

      // THEN
      expect(createurAfricainFormService.getCreateurAfricain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(createurAfricainService.update).toHaveBeenCalledWith(expect.objectContaining(createurAfricain));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreateurAfricain>>();
      const createurAfricain = { id: 123 };
      jest.spyOn(createurAfricainFormService, 'getCreateurAfricain').mockReturnValue({ id: null });
      jest.spyOn(createurAfricainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ createurAfricain: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: createurAfricain }));
      saveSubject.complete();

      // THEN
      expect(createurAfricainFormService.getCreateurAfricain).toHaveBeenCalled();
      expect(createurAfricainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICreateurAfricain>>();
      const createurAfricain = { id: 123 };
      jest.spyOn(createurAfricainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(createurAfricainService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorieCreateurFormService } from './categorie-createur-form.service';
import { CategorieCreateurService } from '../service/categorie-createur.service';
import { ICategorieCreateur } from '../categorie-createur.model';

import { CategorieCreateurUpdateComponent } from './categorie-createur-update.component';

describe('CategorieCreateur Management Update Component', () => {
  let comp: CategorieCreateurUpdateComponent;
  let fixture: ComponentFixture<CategorieCreateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorieCreateurFormService: CategorieCreateurFormService;
  let categorieCreateurService: CategorieCreateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategorieCreateurUpdateComponent],
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
      .overrideTemplate(CategorieCreateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieCreateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorieCreateurFormService = TestBed.inject(CategorieCreateurFormService);
    categorieCreateurService = TestBed.inject(CategorieCreateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categorieCreateur: ICategorieCreateur = { id: 456 };

      activatedRoute.data = of({ categorieCreateur });
      comp.ngOnInit();

      expect(comp.categorieCreateur).toEqual(categorieCreateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieCreateur>>();
      const categorieCreateur = { id: 123 };
      jest.spyOn(categorieCreateurFormService, 'getCategorieCreateur').mockReturnValue(categorieCreateur);
      jest.spyOn(categorieCreateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieCreateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieCreateur }));
      saveSubject.complete();

      // THEN
      expect(categorieCreateurFormService.getCategorieCreateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorieCreateurService.update).toHaveBeenCalledWith(expect.objectContaining(categorieCreateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieCreateur>>();
      const categorieCreateur = { id: 123 };
      jest.spyOn(categorieCreateurFormService, 'getCategorieCreateur').mockReturnValue({ id: null });
      jest.spyOn(categorieCreateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieCreateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieCreateur }));
      saveSubject.complete();

      // THEN
      expect(categorieCreateurFormService.getCategorieCreateur).toHaveBeenCalled();
      expect(categorieCreateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieCreateur>>();
      const categorieCreateur = { id: 123 };
      jest.spyOn(categorieCreateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieCreateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorieCreateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

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
import { IInspiration } from 'app/entities/inspiration/inspiration.model';
import { InspirationService } from 'app/entities/inspiration/service/inspiration.service';
import { ICategorieCreateur } from 'app/entities/categorie-createur/categorie-createur.model';
import { CategorieCreateurService } from 'app/entities/categorie-createur/service/categorie-createur.service';
import { IReseauxSociaux } from 'app/entities/reseaux-sociaux/reseaux-sociaux.model';
import { ReseauxSociauxService } from 'app/entities/reseaux-sociaux/service/reseaux-sociaux.service';

import { CreateurAfricainUpdateComponent } from './createur-africain-update.component';

describe('CreateurAfricain Management Update Component', () => {
  let comp: CreateurAfricainUpdateComponent;
  let fixture: ComponentFixture<CreateurAfricainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let createurAfricainFormService: CreateurAfricainFormService;
  let createurAfricainService: CreateurAfricainService;
  let inspirationService: InspirationService;
  let categorieCreateurService: CategorieCreateurService;
  let reseauxSociauxService: ReseauxSociauxService;

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
    inspirationService = TestBed.inject(InspirationService);
    categorieCreateurService = TestBed.inject(CategorieCreateurService);
    reseauxSociauxService = TestBed.inject(ReseauxSociauxService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Inspiration query and add missing value', () => {
      const createurAfricain: ICreateurAfricain = { id: 456 };
      const inspirations: IInspiration[] = [{ id: 31403 }];
      createurAfricain.inspirations = inspirations;

      const inspirationCollection: IInspiration[] = [{ id: 9785 }];
      jest.spyOn(inspirationService, 'query').mockReturnValue(of(new HttpResponse({ body: inspirationCollection })));
      const additionalInspirations = [...inspirations];
      const expectedCollection: IInspiration[] = [...additionalInspirations, ...inspirationCollection];
      jest.spyOn(inspirationService, 'addInspirationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      expect(inspirationService.query).toHaveBeenCalled();
      expect(inspirationService.addInspirationToCollectionIfMissing).toHaveBeenCalledWith(
        inspirationCollection,
        ...additionalInspirations.map(expect.objectContaining)
      );
      expect(comp.inspirationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategorieCreateur query and add missing value', () => {
      const createurAfricain: ICreateurAfricain = { id: 456 };
      const categorieCreateurs: ICategorieCreateur[] = [{ id: 58957 }];
      createurAfricain.categorieCreateurs = categorieCreateurs;

      const categorieCreateurCollection: ICategorieCreateur[] = [{ id: 69358 }];
      jest.spyOn(categorieCreateurService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCreateurCollection })));
      const additionalCategorieCreateurs = [...categorieCreateurs];
      const expectedCollection: ICategorieCreateur[] = [...additionalCategorieCreateurs, ...categorieCreateurCollection];
      jest.spyOn(categorieCreateurService, 'addCategorieCreateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      expect(categorieCreateurService.query).toHaveBeenCalled();
      expect(categorieCreateurService.addCategorieCreateurToCollectionIfMissing).toHaveBeenCalledWith(
        categorieCreateurCollection,
        ...additionalCategorieCreateurs.map(expect.objectContaining)
      );
      expect(comp.categorieCreateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ReseauxSociaux query and add missing value', () => {
      const createurAfricain: ICreateurAfricain = { id: 456 };
      const reseauxSociauxes: IReseauxSociaux[] = [{ id: 12735 }];
      createurAfricain.reseauxSociauxes = reseauxSociauxes;

      const reseauxSociauxCollection: IReseauxSociaux[] = [{ id: 23826 }];
      jest.spyOn(reseauxSociauxService, 'query').mockReturnValue(of(new HttpResponse({ body: reseauxSociauxCollection })));
      const additionalReseauxSociauxes = [...reseauxSociauxes];
      const expectedCollection: IReseauxSociaux[] = [...additionalReseauxSociauxes, ...reseauxSociauxCollection];
      jest.spyOn(reseauxSociauxService, 'addReseauxSociauxToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      expect(reseauxSociauxService.query).toHaveBeenCalled();
      expect(reseauxSociauxService.addReseauxSociauxToCollectionIfMissing).toHaveBeenCalledWith(
        reseauxSociauxCollection,
        ...additionalReseauxSociauxes.map(expect.objectContaining)
      );
      expect(comp.reseauxSociauxesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const createurAfricain: ICreateurAfricain = { id: 456 };
      const inspiration: IInspiration = { id: 59786 };
      createurAfricain.inspirations = [inspiration];
      const categorieCreateur: ICategorieCreateur = { id: 95360 };
      createurAfricain.categorieCreateurs = [categorieCreateur];
      const reseauxSociaux: IReseauxSociaux = { id: 78588 };
      createurAfricain.reseauxSociauxes = [reseauxSociaux];

      activatedRoute.data = of({ createurAfricain });
      comp.ngOnInit();

      expect(comp.inspirationsSharedCollection).toContain(inspiration);
      expect(comp.categorieCreateursSharedCollection).toContain(categorieCreateur);
      expect(comp.reseauxSociauxesSharedCollection).toContain(reseauxSociaux);
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

  describe('Compare relationships', () => {
    describe('compareInspiration', () => {
      it('Should forward to inspirationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(inspirationService, 'compareInspiration');
        comp.compareInspiration(entity, entity2);
        expect(inspirationService.compareInspiration).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategorieCreateur', () => {
      it('Should forward to categorieCreateurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categorieCreateurService, 'compareCategorieCreateur');
        comp.compareCategorieCreateur(entity, entity2);
        expect(categorieCreateurService.compareCategorieCreateur).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareReseauxSociaux', () => {
      it('Should forward to reseauxSociauxService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reseauxSociauxService, 'compareReseauxSociaux');
        comp.compareReseauxSociaux(entity, entity2);
        expect(reseauxSociauxService.compareReseauxSociaux).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

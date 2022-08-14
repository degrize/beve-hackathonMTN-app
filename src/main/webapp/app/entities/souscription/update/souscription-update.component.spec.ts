import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SouscriptionFormService } from './souscription-form.service';
import { SouscriptionService } from '../service/souscription.service';
import { ISouscription } from '../souscription.model';

import { SouscriptionUpdateComponent } from './souscription-update.component';

describe('Souscription Management Update Component', () => {
  let comp: SouscriptionUpdateComponent;
  let fixture: ComponentFixture<SouscriptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let souscriptionFormService: SouscriptionFormService;
  let souscriptionService: SouscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SouscriptionUpdateComponent],
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
      .overrideTemplate(SouscriptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SouscriptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    souscriptionFormService = TestBed.inject(SouscriptionFormService);
    souscriptionService = TestBed.inject(SouscriptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const souscription: ISouscription = { id: 456 };

      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      expect(comp.souscription).toEqual(souscription);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionFormService, 'getSouscription').mockReturnValue(souscription);
      jest.spyOn(souscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: souscription }));
      saveSubject.complete();

      // THEN
      expect(souscriptionFormService.getSouscription).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(souscriptionService.update).toHaveBeenCalledWith(expect.objectContaining(souscription));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionFormService, 'getSouscription').mockReturnValue({ id: null });
      jest.spyOn(souscriptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: souscription }));
      saveSubject.complete();

      // THEN
      expect(souscriptionFormService.getSouscription).toHaveBeenCalled();
      expect(souscriptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(souscriptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

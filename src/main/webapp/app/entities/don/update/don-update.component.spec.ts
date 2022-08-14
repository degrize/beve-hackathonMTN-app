import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonFormService } from './don-form.service';
import { DonService } from '../service/don.service';
import { IDon } from '../don.model';
import { ITransaction } from 'app/entities/transaction/transaction.model';
import { TransactionService } from 'app/entities/transaction/service/transaction.service';
import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';
import { CreateurAfricainService } from 'app/entities/createur-africain/service/createur-africain.service';
import { IDonnateur } from 'app/entities/donnateur/donnateur.model';
import { DonnateurService } from 'app/entities/donnateur/service/donnateur.service';

import { DonUpdateComponent } from './don-update.component';

describe('Don Management Update Component', () => {
  let comp: DonUpdateComponent;
  let fixture: ComponentFixture<DonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donFormService: DonFormService;
  let donService: DonService;
  let transactionService: TransactionService;
  let createurAfricainService: CreateurAfricainService;
  let donnateurService: DonnateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonUpdateComponent],
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
      .overrideTemplate(DonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donFormService = TestBed.inject(DonFormService);
    donService = TestBed.inject(DonService);
    transactionService = TestBed.inject(TransactionService);
    createurAfricainService = TestBed.inject(CreateurAfricainService);
    donnateurService = TestBed.inject(DonnateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Transaction query and add missing value', () => {
      const don: IDon = { id: 456 };
      const transaction: ITransaction = { id: 12760 };
      don.transaction = transaction;

      const transactionCollection: ITransaction[] = [{ id: 73710 }];
      jest.spyOn(transactionService, 'query').mockReturnValue(of(new HttpResponse({ body: transactionCollection })));
      const additionalTransactions = [transaction];
      const expectedCollection: ITransaction[] = [...additionalTransactions, ...transactionCollection];
      jest.spyOn(transactionService, 'addTransactionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ don });
      comp.ngOnInit();

      expect(transactionService.query).toHaveBeenCalled();
      expect(transactionService.addTransactionToCollectionIfMissing).toHaveBeenCalledWith(
        transactionCollection,
        ...additionalTransactions.map(expect.objectContaining)
      );
      expect(comp.transactionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CreateurAfricain query and add missing value', () => {
      const don: IDon = { id: 456 };
      const createurAfricain: ICreateurAfricain = { id: 76170 };
      don.createurAfricain = createurAfricain;

      const createurAfricainCollection: ICreateurAfricain[] = [{ id: 11758 }];
      jest.spyOn(createurAfricainService, 'query').mockReturnValue(of(new HttpResponse({ body: createurAfricainCollection })));
      const additionalCreateurAfricains = [createurAfricain];
      const expectedCollection: ICreateurAfricain[] = [...additionalCreateurAfricains, ...createurAfricainCollection];
      jest.spyOn(createurAfricainService, 'addCreateurAfricainToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ don });
      comp.ngOnInit();

      expect(createurAfricainService.query).toHaveBeenCalled();
      expect(createurAfricainService.addCreateurAfricainToCollectionIfMissing).toHaveBeenCalledWith(
        createurAfricainCollection,
        ...additionalCreateurAfricains.map(expect.objectContaining)
      );
      expect(comp.createurAfricainsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Donnateur query and add missing value', () => {
      const don: IDon = { id: 456 };
      const donnateur: IDonnateur = { id: 63166 };
      don.donnateur = donnateur;

      const donnateurCollection: IDonnateur[] = [{ id: 20935 }];
      jest.spyOn(donnateurService, 'query').mockReturnValue(of(new HttpResponse({ body: donnateurCollection })));
      const additionalDonnateurs = [donnateur];
      const expectedCollection: IDonnateur[] = [...additionalDonnateurs, ...donnateurCollection];
      jest.spyOn(donnateurService, 'addDonnateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ don });
      comp.ngOnInit();

      expect(donnateurService.query).toHaveBeenCalled();
      expect(donnateurService.addDonnateurToCollectionIfMissing).toHaveBeenCalledWith(
        donnateurCollection,
        ...additionalDonnateurs.map(expect.objectContaining)
      );
      expect(comp.donnateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const don: IDon = { id: 456 };
      const transaction: ITransaction = { id: 47695 };
      don.transaction = transaction;
      const createurAfricain: ICreateurAfricain = { id: 51738 };
      don.createurAfricain = createurAfricain;
      const donnateur: IDonnateur = { id: 73717 };
      don.donnateur = donnateur;

      activatedRoute.data = of({ don });
      comp.ngOnInit();

      expect(comp.transactionsSharedCollection).toContain(transaction);
      expect(comp.createurAfricainsSharedCollection).toContain(createurAfricain);
      expect(comp.donnateursSharedCollection).toContain(donnateur);
      expect(comp.don).toEqual(don);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDon>>();
      const don = { id: 123 };
      jest.spyOn(donFormService, 'getDon').mockReturnValue(don);
      jest.spyOn(donService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ don });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: don }));
      saveSubject.complete();

      // THEN
      expect(donFormService.getDon).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(donService.update).toHaveBeenCalledWith(expect.objectContaining(don));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDon>>();
      const don = { id: 123 };
      jest.spyOn(donFormService, 'getDon').mockReturnValue({ id: null });
      jest.spyOn(donService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ don: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: don }));
      saveSubject.complete();

      // THEN
      expect(donFormService.getDon).toHaveBeenCalled();
      expect(donService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDon>>();
      const don = { id: 123 };
      jest.spyOn(donService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ don });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTransaction', () => {
      it('Should forward to transactionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(transactionService, 'compareTransaction');
        comp.compareTransaction(entity, entity2);
        expect(transactionService.compareTransaction).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCreateurAfricain', () => {
      it('Should forward to createurAfricainService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(createurAfricainService, 'compareCreateurAfricain');
        comp.compareCreateurAfricain(entity, entity2);
        expect(createurAfricainService.compareCreateurAfricain).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDonnateur', () => {
      it('Should forward to donnateurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(donnateurService, 'compareDonnateur');
        comp.compareDonnateur(entity, entity2);
        expect(donnateurService.compareDonnateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DonFormService, DonFormGroup } from './don-form.service';
import { IDon } from '../don.model';
import { DonService } from '../service/don.service';
import { ITransaction } from 'app/entities/transaction/transaction.model';
import { TransactionService } from 'app/entities/transaction/service/transaction.service';
import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';
import { CreateurAfricainService } from 'app/entities/createur-africain/service/createur-africain.service';
import { IDonnateur } from 'app/entities/donnateur/donnateur.model';
import { DonnateurService } from 'app/entities/donnateur/service/donnateur.service';

@Component({
  selector: 'jhi-don-update',
  templateUrl: './don-update.component.html',
})
export class DonUpdateComponent implements OnInit {
  isSaving = false;
  don: IDon | null = null;

  transactionsSharedCollection: ITransaction[] = [];
  createurAfricainsSharedCollection: ICreateurAfricain[] = [];
  donnateursSharedCollection: IDonnateur[] = [];

  editForm: DonFormGroup = this.donFormService.createDonFormGroup();

  constructor(
    protected donService: DonService,
    protected donFormService: DonFormService,
    protected transactionService: TransactionService,
    protected createurAfricainService: CreateurAfricainService,
    protected donnateurService: DonnateurService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTransaction = (o1: ITransaction | null, o2: ITransaction | null): boolean => this.transactionService.compareTransaction(o1, o2);

  compareCreateurAfricain = (o1: ICreateurAfricain | null, o2: ICreateurAfricain | null): boolean =>
    this.createurAfricainService.compareCreateurAfricain(o1, o2);

  compareDonnateur = (o1: IDonnateur | null, o2: IDonnateur | null): boolean => this.donnateurService.compareDonnateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ don }) => {
      this.don = don;
      if (don) {
        this.updateForm(don);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const don = this.donFormService.getDon(this.editForm);
    if (don.id !== null) {
      this.subscribeToSaveResponse(this.donService.update(don));
    } else {
      this.subscribeToSaveResponse(this.donService.create(don));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDon>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(don: IDon): void {
    this.don = don;
    this.donFormService.resetForm(this.editForm, don);

    this.transactionsSharedCollection = this.transactionService.addTransactionToCollectionIfMissing<ITransaction>(
      this.transactionsSharedCollection,
      don.transaction
    );
    this.createurAfricainsSharedCollection = this.createurAfricainService.addCreateurAfricainToCollectionIfMissing<ICreateurAfricain>(
      this.createurAfricainsSharedCollection,
      don.createurAfricain
    );
    this.donnateursSharedCollection = this.donnateurService.addDonnateurToCollectionIfMissing<IDonnateur>(
      this.donnateursSharedCollection,
      don.donnateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.transactionService
      .query()
      .pipe(map((res: HttpResponse<ITransaction[]>) => res.body ?? []))
      .pipe(
        map((transactions: ITransaction[]) =>
          this.transactionService.addTransactionToCollectionIfMissing<ITransaction>(transactions, this.don?.transaction)
        )
      )
      .subscribe((transactions: ITransaction[]) => (this.transactionsSharedCollection = transactions));

    this.createurAfricainService
      .query()
      .pipe(map((res: HttpResponse<ICreateurAfricain[]>) => res.body ?? []))
      .pipe(
        map((createurAfricains: ICreateurAfricain[]) =>
          this.createurAfricainService.addCreateurAfricainToCollectionIfMissing<ICreateurAfricain>(
            createurAfricains,
            this.don?.createurAfricain
          )
        )
      )
      .subscribe((createurAfricains: ICreateurAfricain[]) => (this.createurAfricainsSharedCollection = createurAfricains));

    this.donnateurService
      .query()
      .pipe(map((res: HttpResponse<IDonnateur[]>) => res.body ?? []))
      .pipe(
        map((donnateurs: IDonnateur[]) =>
          this.donnateurService.addDonnateurToCollectionIfMissing<IDonnateur>(donnateurs, this.don?.donnateur)
        )
      )
      .subscribe((donnateurs: IDonnateur[]) => (this.donnateursSharedCollection = donnateurs));
  }
}

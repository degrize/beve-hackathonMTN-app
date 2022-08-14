import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SouscriptionFormService, SouscriptionFormGroup } from './souscription-form.service';
import { ISouscription } from '../souscription.model';
import { SouscriptionService } from '../service/souscription.service';
import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';
import { CreateurAfricainService } from 'app/entities/createur-africain/service/createur-africain.service';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

@Component({
  selector: 'jhi-souscription-update',
  templateUrl: './souscription-update.component.html',
})
export class SouscriptionUpdateComponent implements OnInit {
  isSaving = false;
  souscription: ISouscription | null = null;
  etatCompteValues = Object.keys(EtatCompte);

  createurAfricainsSharedCollection: ICreateurAfricain[] = [];

  editForm: SouscriptionFormGroup = this.souscriptionFormService.createSouscriptionFormGroup();

  constructor(
    protected souscriptionService: SouscriptionService,
    protected souscriptionFormService: SouscriptionFormService,
    protected createurAfricainService: CreateurAfricainService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCreateurAfricain = (o1: ICreateurAfricain | null, o2: ICreateurAfricain | null): boolean =>
    this.createurAfricainService.compareCreateurAfricain(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ souscription }) => {
      this.souscription = souscription;
      if (souscription) {
        this.updateForm(souscription);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const souscription = this.souscriptionFormService.getSouscription(this.editForm);
    if (souscription.id !== null) {
      this.subscribeToSaveResponse(this.souscriptionService.update(souscription));
    } else {
      this.subscribeToSaveResponse(this.souscriptionService.create(souscription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISouscription>>): void {
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

  protected updateForm(souscription: ISouscription): void {
    this.souscription = souscription;
    this.souscriptionFormService.resetForm(this.editForm, souscription);

    this.createurAfricainsSharedCollection = this.createurAfricainService.addCreateurAfricainToCollectionIfMissing<ICreateurAfricain>(
      this.createurAfricainsSharedCollection,
      ...(souscription.createurAfricains ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.createurAfricainService
      .query()
      .pipe(map((res: HttpResponse<ICreateurAfricain[]>) => res.body ?? []))
      .pipe(
        map((createurAfricains: ICreateurAfricain[]) =>
          this.createurAfricainService.addCreateurAfricainToCollectionIfMissing<ICreateurAfricain>(
            createurAfricains,
            ...(this.souscription?.createurAfricains ?? [])
          )
        )
      )
      .subscribe((createurAfricains: ICreateurAfricain[]) => (this.createurAfricainsSharedCollection = createurAfricains));
  }
}

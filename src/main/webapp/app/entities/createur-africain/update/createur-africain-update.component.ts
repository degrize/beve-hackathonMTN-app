import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CreateurAfricainFormService, CreateurAfricainFormGroup } from './createur-africain-form.service';
import { ICreateurAfricain } from '../createur-africain.model';
import { CreateurAfricainService } from '../service/createur-africain.service';
import { IInspiration } from 'app/entities/inspiration/inspiration.model';
import { InspirationService } from 'app/entities/inspiration/service/inspiration.service';
import { ICategorieCreateur } from 'app/entities/categorie-createur/categorie-createur.model';
import { CategorieCreateurService } from 'app/entities/categorie-createur/service/categorie-createur.service';
import { IReseauxSociaux } from 'app/entities/reseaux-sociaux/reseaux-sociaux.model';
import { ReseauxSociauxService } from 'app/entities/reseaux-sociaux/service/reseaux-sociaux.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

@Component({
  selector: 'jhi-createur-africain-update',
  templateUrl: './createur-africain-update.component.html',
})
export class CreateurAfricainUpdateComponent implements OnInit {
  isSaving = false;
  createurAfricain: ICreateurAfricain | null = null;
  sexeValues = Object.keys(Sexe);
  situationMatrimonialeValues = Object.keys(SituationMatrimoniale);

  inspirationsSharedCollection: IInspiration[] = [];
  categorieCreateursSharedCollection: ICategorieCreateur[] = [];
  reseauxSociauxesSharedCollection: IReseauxSociaux[] = [];

  editForm: CreateurAfricainFormGroup = this.createurAfricainFormService.createCreateurAfricainFormGroup();

  constructor(
    protected createurAfricainService: CreateurAfricainService,
    protected createurAfricainFormService: CreateurAfricainFormService,
    protected inspirationService: InspirationService,
    protected categorieCreateurService: CategorieCreateurService,
    protected reseauxSociauxService: ReseauxSociauxService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInspiration = (o1: IInspiration | null, o2: IInspiration | null): boolean => this.inspirationService.compareInspiration(o1, o2);

  compareCategorieCreateur = (o1: ICategorieCreateur | null, o2: ICategorieCreateur | null): boolean =>
    this.categorieCreateurService.compareCategorieCreateur(o1, o2);

  compareReseauxSociaux = (o1: IReseauxSociaux | null, o2: IReseauxSociaux | null): boolean =>
    this.reseauxSociauxService.compareReseauxSociaux(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ createurAfricain }) => {
      this.createurAfricain = createurAfricain;
      if (createurAfricain) {
        this.updateForm(createurAfricain);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const createurAfricain = this.createurAfricainFormService.getCreateurAfricain(this.editForm);
    if (createurAfricain.id !== null) {
      this.subscribeToSaveResponse(this.createurAfricainService.update(createurAfricain));
    } else {
      this.subscribeToSaveResponse(this.createurAfricainService.create(createurAfricain));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreateurAfricain>>): void {
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

  protected updateForm(createurAfricain: ICreateurAfricain): void {
    this.createurAfricain = createurAfricain;
    this.createurAfricainFormService.resetForm(this.editForm, createurAfricain);

    this.inspirationsSharedCollection = this.inspirationService.addInspirationToCollectionIfMissing<IInspiration>(
      this.inspirationsSharedCollection,
      ...(createurAfricain.inspirations ?? [])
    );
    this.categorieCreateursSharedCollection = this.categorieCreateurService.addCategorieCreateurToCollectionIfMissing<ICategorieCreateur>(
      this.categorieCreateursSharedCollection,
      ...(createurAfricain.categorieCreateurs ?? [])
    );
    this.reseauxSociauxesSharedCollection = this.reseauxSociauxService.addReseauxSociauxToCollectionIfMissing<IReseauxSociaux>(
      this.reseauxSociauxesSharedCollection,
      ...(createurAfricain.reseauxSociauxes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inspirationService
      .query()
      .pipe(map((res: HttpResponse<IInspiration[]>) => res.body ?? []))
      .pipe(
        map((inspirations: IInspiration[]) =>
          this.inspirationService.addInspirationToCollectionIfMissing<IInspiration>(
            inspirations,
            ...(this.createurAfricain?.inspirations ?? [])
          )
        )
      )
      .subscribe((inspirations: IInspiration[]) => (this.inspirationsSharedCollection = inspirations));

    this.categorieCreateurService
      .query()
      .pipe(map((res: HttpResponse<ICategorieCreateur[]>) => res.body ?? []))
      .pipe(
        map((categorieCreateurs: ICategorieCreateur[]) =>
          this.categorieCreateurService.addCategorieCreateurToCollectionIfMissing<ICategorieCreateur>(
            categorieCreateurs,
            ...(this.createurAfricain?.categorieCreateurs ?? [])
          )
        )
      )
      .subscribe((categorieCreateurs: ICategorieCreateur[]) => (this.categorieCreateursSharedCollection = categorieCreateurs));

    this.reseauxSociauxService
      .query()
      .pipe(map((res: HttpResponse<IReseauxSociaux[]>) => res.body ?? []))
      .pipe(
        map((reseauxSociauxes: IReseauxSociaux[]) =>
          this.reseauxSociauxService.addReseauxSociauxToCollectionIfMissing<IReseauxSociaux>(
            reseauxSociauxes,
            ...(this.createurAfricain?.reseauxSociauxes ?? [])
          )
        )
      )
      .subscribe((reseauxSociauxes: IReseauxSociaux[]) => (this.reseauxSociauxesSharedCollection = reseauxSociauxes));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReseauxSociauxFormService, ReseauxSociauxFormGroup } from './reseaux-sociaux-form.service';
import { IReseauxSociaux } from '../reseaux-sociaux.model';
import { ReseauxSociauxService } from '../service/reseaux-sociaux.service';

@Component({
  selector: 'jhi-reseaux-sociaux-update',
  templateUrl: './reseaux-sociaux-update.component.html',
})
export class ReseauxSociauxUpdateComponent implements OnInit {
  isSaving = false;
  reseauxSociaux: IReseauxSociaux | null = null;

  editForm: ReseauxSociauxFormGroup = this.reseauxSociauxFormService.createReseauxSociauxFormGroup();

  constructor(
    protected reseauxSociauxService: ReseauxSociauxService,
    protected reseauxSociauxFormService: ReseauxSociauxFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reseauxSociaux }) => {
      this.reseauxSociaux = reseauxSociaux;
      if (reseauxSociaux) {
        this.updateForm(reseauxSociaux);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reseauxSociaux = this.reseauxSociauxFormService.getReseauxSociaux(this.editForm);
    if (reseauxSociaux.id !== null) {
      this.subscribeToSaveResponse(this.reseauxSociauxService.update(reseauxSociaux));
    } else {
      this.subscribeToSaveResponse(this.reseauxSociauxService.create(reseauxSociaux));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReseauxSociaux>>): void {
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

  protected updateForm(reseauxSociaux: IReseauxSociaux): void {
    this.reseauxSociaux = reseauxSociaux;
    this.reseauxSociauxFormService.resetForm(this.editForm, reseauxSociaux);
  }
}

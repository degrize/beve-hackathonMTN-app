import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NatureCreateurFormService, NatureCreateurFormGroup } from './nature-createur-form.service';
import { INatureCreateur } from '../nature-createur.model';
import { NatureCreateurService } from '../service/nature-createur.service';

@Component({
  selector: 'jhi-nature-createur-update',
  templateUrl: './nature-createur-update.component.html',
})
export class NatureCreateurUpdateComponent implements OnInit {
  isSaving = false;
  natureCreateur: INatureCreateur | null = null;

  editForm: NatureCreateurFormGroup = this.natureCreateurFormService.createNatureCreateurFormGroup();

  constructor(
    protected natureCreateurService: NatureCreateurService,
    protected natureCreateurFormService: NatureCreateurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureCreateur }) => {
      this.natureCreateur = natureCreateur;
      if (natureCreateur) {
        this.updateForm(natureCreateur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureCreateur = this.natureCreateurFormService.getNatureCreateur(this.editForm);
    if (natureCreateur.id !== null) {
      this.subscribeToSaveResponse(this.natureCreateurService.update(natureCreateur));
    } else {
      this.subscribeToSaveResponse(this.natureCreateurService.create(natureCreateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureCreateur>>): void {
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

  protected updateForm(natureCreateur: INatureCreateur): void {
    this.natureCreateur = natureCreateur;
    this.natureCreateurFormService.resetForm(this.editForm, natureCreateur);
  }
}

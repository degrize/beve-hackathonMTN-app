import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { InspirationFormService, InspirationFormGroup } from './inspiration-form.service';
import { IInspiration } from '../inspiration.model';
import { InspirationService } from '../service/inspiration.service';

@Component({
  selector: 'jhi-inspiration-update',
  templateUrl: './inspiration-update.component.html',
})
export class InspirationUpdateComponent implements OnInit {
  isSaving = false;
  inspiration: IInspiration | null = null;

  editForm: InspirationFormGroup = this.inspirationFormService.createInspirationFormGroup();

  constructor(
    protected inspirationService: InspirationService,
    protected inspirationFormService: InspirationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inspiration }) => {
      this.inspiration = inspiration;
      if (inspiration) {
        this.updateForm(inspiration);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inspiration = this.inspirationFormService.getInspiration(this.editForm);
    if (inspiration.id !== null) {
      this.subscribeToSaveResponse(this.inspirationService.update(inspiration));
    } else {
      this.subscribeToSaveResponse(this.inspirationService.create(inspiration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspiration>>): void {
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

  protected updateForm(inspiration: IInspiration): void {
    this.inspiration = inspiration;
    this.inspirationFormService.resetForm(this.editForm, inspiration);
  }
}

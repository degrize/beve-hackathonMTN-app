import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategorieCreateurFormService, CategorieCreateurFormGroup } from './categorie-createur-form.service';
import { ICategorieCreateur } from '../categorie-createur.model';
import { CategorieCreateurService } from '../service/categorie-createur.service';

@Component({
  selector: 'jhi-categorie-createur-update',
  templateUrl: './categorie-createur-update.component.html',
})
export class CategorieCreateurUpdateComponent implements OnInit {
  isSaving = false;
  categorieCreateur: ICategorieCreateur | null = null;

  editForm: CategorieCreateurFormGroup = this.categorieCreateurFormService.createCategorieCreateurFormGroup();

  constructor(
    protected categorieCreateurService: CategorieCreateurService,
    protected categorieCreateurFormService: CategorieCreateurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCreateur }) => {
      this.categorieCreateur = categorieCreateur;
      if (categorieCreateur) {
        this.updateForm(categorieCreateur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieCreateur = this.categorieCreateurFormService.getCategorieCreateur(this.editForm);
    if (categorieCreateur.id !== null) {
      this.subscribeToSaveResponse(this.categorieCreateurService.update(categorieCreateur));
    } else {
      this.subscribeToSaveResponse(this.categorieCreateurService.create(categorieCreateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieCreateur>>): void {
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

  protected updateForm(categorieCreateur: ICategorieCreateur): void {
    this.categorieCreateur = categorieCreateur;
    this.categorieCreateurFormService.resetForm(this.editForm, categorieCreateur);
  }
}

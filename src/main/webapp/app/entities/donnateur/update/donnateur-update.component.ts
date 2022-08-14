import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DonnateurFormService, DonnateurFormGroup } from './donnateur-form.service';
import { IDonnateur } from '../donnateur.model';
import { DonnateurService } from '../service/donnateur.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Forfait } from 'app/entities/enumerations/forfait.model';

@Component({
  selector: 'jhi-donnateur-update',
  templateUrl: './donnateur-update.component.html',
})
export class DonnateurUpdateComponent implements OnInit {
  isSaving = false;
  donnateur: IDonnateur | null = null;
  sexeValues = Object.keys(Sexe);
  forfaitValues = Object.keys(Forfait);

  editForm: DonnateurFormGroup = this.donnateurFormService.createDonnateurFormGroup();

  constructor(
    protected donnateurService: DonnateurService,
    protected donnateurFormService: DonnateurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donnateur }) => {
      this.donnateur = donnateur;
      if (donnateur) {
        this.updateForm(donnateur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donnateur = this.donnateurFormService.getDonnateur(this.editForm);
    if (donnateur.id !== null) {
      this.subscribeToSaveResponse(this.donnateurService.update(donnateur));
    } else {
      this.subscribeToSaveResponse(this.donnateurService.create(donnateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonnateur>>): void {
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

  protected updateForm(donnateur: IDonnateur): void {
    this.donnateur = donnateur;
    this.donnateurFormService.resetForm(this.editForm, donnateur);
  }
}

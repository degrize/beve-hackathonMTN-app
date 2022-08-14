import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SouscriptionFormService, SouscriptionFormGroup } from './souscription-form.service';
import { ISouscription } from '../souscription.model';
import { SouscriptionService } from '../service/souscription.service';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

@Component({
  selector: 'jhi-souscription-update',
  templateUrl: './souscription-update.component.html',
})
export class SouscriptionUpdateComponent implements OnInit {
  isSaving = false;
  souscription: ISouscription | null = null;
  etatCompteValues = Object.keys(EtatCompte);

  editForm: SouscriptionFormGroup = this.souscriptionFormService.createSouscriptionFormGroup();

  constructor(
    protected souscriptionService: SouscriptionService,
    protected souscriptionFormService: SouscriptionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ souscription }) => {
      this.souscription = souscription;
      if (souscription) {
        this.updateForm(souscription);
      }
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
  }
}

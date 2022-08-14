import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReseauxSociaux } from '../reseaux-sociaux.model';

@Component({
  selector: 'jhi-reseaux-sociaux-detail',
  templateUrl: './reseaux-sociaux-detail.component.html',
})
export class ReseauxSociauxDetailComponent implements OnInit {
  reseauxSociaux: IReseauxSociaux | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reseauxSociaux }) => {
      this.reseauxSociaux = reseauxSociaux;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

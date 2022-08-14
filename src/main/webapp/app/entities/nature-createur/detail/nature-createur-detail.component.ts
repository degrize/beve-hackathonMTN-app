import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureCreateur } from '../nature-createur.model';

@Component({
  selector: 'jhi-nature-createur-detail',
  templateUrl: './nature-createur-detail.component.html',
})
export class NatureCreateurDetailComponent implements OnInit {
  natureCreateur: INatureCreateur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureCreateur }) => {
      this.natureCreateur = natureCreateur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

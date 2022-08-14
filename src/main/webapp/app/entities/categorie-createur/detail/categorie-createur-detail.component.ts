import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieCreateur } from '../categorie-createur.model';

@Component({
  selector: 'jhi-categorie-createur-detail',
  templateUrl: './categorie-createur-detail.component.html',
})
export class CategorieCreateurDetailComponent implements OnInit {
  categorieCreateur: ICategorieCreateur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCreateur }) => {
      this.categorieCreateur = categorieCreateur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

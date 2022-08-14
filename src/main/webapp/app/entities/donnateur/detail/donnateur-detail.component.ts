import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonnateur } from '../donnateur.model';

@Component({
  selector: 'jhi-donnateur-detail',
  templateUrl: './donnateur-detail.component.html',
})
export class DonnateurDetailComponent implements OnInit {
  donnateur: IDonnateur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donnateur }) => {
      this.donnateur = donnateur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInspiration } from '../inspiration.model';

@Component({
  selector: 'jhi-inspiration-detail',
  templateUrl: './inspiration-detail.component.html',
})
export class InspirationDetailComponent implements OnInit {
  inspiration: IInspiration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inspiration }) => {
      this.inspiration = inspiration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

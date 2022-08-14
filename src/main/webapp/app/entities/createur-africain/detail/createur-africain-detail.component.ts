import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreateurAfricain } from '../createur-africain.model';

@Component({
  selector: 'jhi-createur-africain-detail',
  templateUrl: './createur-africain-detail.component.html',
})
export class CreateurAfricainDetailComponent implements OnInit {
  createurAfricain: ICreateurAfricain | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ createurAfricain }) => {
      this.createurAfricain = createurAfricain;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

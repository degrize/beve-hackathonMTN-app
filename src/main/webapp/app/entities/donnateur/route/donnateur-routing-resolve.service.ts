import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonnateur } from '../donnateur.model';
import { DonnateurService } from '../service/donnateur.service';

@Injectable({ providedIn: 'root' })
export class DonnateurRoutingResolveService implements Resolve<IDonnateur | null> {
  constructor(protected service: DonnateurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonnateur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donnateur: HttpResponse<IDonnateur>) => {
          if (donnateur.body) {
            return of(donnateur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

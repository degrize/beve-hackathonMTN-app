import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDon } from '../don.model';
import { DonService } from '../service/don.service';

@Injectable({ providedIn: 'root' })
export class DonRoutingResolveService implements Resolve<IDon | null> {
  constructor(protected service: DonService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDon | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((don: HttpResponse<IDon>) => {
          if (don.body) {
            return of(don.body);
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

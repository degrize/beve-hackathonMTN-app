import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInspiration } from '../inspiration.model';
import { InspirationService } from '../service/inspiration.service';

@Injectable({ providedIn: 'root' })
export class InspirationRoutingResolveService implements Resolve<IInspiration | null> {
  constructor(protected service: InspirationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInspiration | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inspiration: HttpResponse<IInspiration>) => {
          if (inspiration.body) {
            return of(inspiration.body);
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

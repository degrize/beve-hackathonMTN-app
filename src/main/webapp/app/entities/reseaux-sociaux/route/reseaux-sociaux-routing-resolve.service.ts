import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReseauxSociaux } from '../reseaux-sociaux.model';
import { ReseauxSociauxService } from '../service/reseaux-sociaux.service';

@Injectable({ providedIn: 'root' })
export class ReseauxSociauxRoutingResolveService implements Resolve<IReseauxSociaux | null> {
  constructor(protected service: ReseauxSociauxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReseauxSociaux | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reseauxSociaux: HttpResponse<IReseauxSociaux>) => {
          if (reseauxSociaux.body) {
            return of(reseauxSociaux.body);
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

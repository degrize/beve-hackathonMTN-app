import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureCreateur } from '../nature-createur.model';
import { NatureCreateurService } from '../service/nature-createur.service';

@Injectable({ providedIn: 'root' })
export class NatureCreateurRoutingResolveService implements Resolve<INatureCreateur | null> {
  constructor(protected service: NatureCreateurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureCreateur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureCreateur: HttpResponse<INatureCreateur>) => {
          if (natureCreateur.body) {
            return of(natureCreateur.body);
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

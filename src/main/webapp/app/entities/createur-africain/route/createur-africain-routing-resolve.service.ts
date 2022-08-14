import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICreateurAfricain } from '../createur-africain.model';
import { CreateurAfricainService } from '../service/createur-africain.service';

@Injectable({ providedIn: 'root' })
export class CreateurAfricainRoutingResolveService implements Resolve<ICreateurAfricain | null> {
  constructor(protected service: CreateurAfricainService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICreateurAfricain | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((createurAfricain: HttpResponse<ICreateurAfricain>) => {
          if (createurAfricain.body) {
            return of(createurAfricain.body);
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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieCreateur } from '../categorie-createur.model';
import { CategorieCreateurService } from '../service/categorie-createur.service';

@Injectable({ providedIn: 'root' })
export class CategorieCreateurRoutingResolveService implements Resolve<ICategorieCreateur | null> {
  constructor(protected service: CategorieCreateurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieCreateur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieCreateur: HttpResponse<ICategorieCreateur>) => {
          if (categorieCreateur.body) {
            return of(categorieCreateur.body);
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

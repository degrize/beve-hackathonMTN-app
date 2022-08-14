import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieCreateurComponent } from '../list/categorie-createur.component';
import { CategorieCreateurDetailComponent } from '../detail/categorie-createur-detail.component';
import { CategorieCreateurUpdateComponent } from '../update/categorie-createur-update.component';
import { CategorieCreateurRoutingResolveService } from './categorie-createur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categorieCreateurRoute: Routes = [
  {
    path: '',
    component: CategorieCreateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieCreateurDetailComponent,
    resolve: {
      categorieCreateur: CategorieCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieCreateurUpdateComponent,
    resolve: {
      categorieCreateur: CategorieCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieCreateurUpdateComponent,
    resolve: {
      categorieCreateur: CategorieCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieCreateurRoute)],
  exports: [RouterModule],
})
export class CategorieCreateurRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureCreateurComponent } from '../list/nature-createur.component';
import { NatureCreateurDetailComponent } from '../detail/nature-createur-detail.component';
import { NatureCreateurUpdateComponent } from '../update/nature-createur-update.component';
import { NatureCreateurRoutingResolveService } from './nature-createur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const natureCreateurRoute: Routes = [
  {
    path: '',
    component: NatureCreateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureCreateurDetailComponent,
    resolve: {
      natureCreateur: NatureCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureCreateurUpdateComponent,
    resolve: {
      natureCreateur: NatureCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureCreateurUpdateComponent,
    resolve: {
      natureCreateur: NatureCreateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureCreateurRoute)],
  exports: [RouterModule],
})
export class NatureCreateurRoutingModule {}

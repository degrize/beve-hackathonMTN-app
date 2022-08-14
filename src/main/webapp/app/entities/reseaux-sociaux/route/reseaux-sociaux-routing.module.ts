import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReseauxSociauxComponent } from '../list/reseaux-sociaux.component';
import { ReseauxSociauxDetailComponent } from '../detail/reseaux-sociaux-detail.component';
import { ReseauxSociauxUpdateComponent } from '../update/reseaux-sociaux-update.component';
import { ReseauxSociauxRoutingResolveService } from './reseaux-sociaux-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reseauxSociauxRoute: Routes = [
  {
    path: '',
    component: ReseauxSociauxComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReseauxSociauxDetailComponent,
    resolve: {
      reseauxSociaux: ReseauxSociauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReseauxSociauxUpdateComponent,
    resolve: {
      reseauxSociaux: ReseauxSociauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReseauxSociauxUpdateComponent,
    resolve: {
      reseauxSociaux: ReseauxSociauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reseauxSociauxRoute)],
  exports: [RouterModule],
})
export class ReseauxSociauxRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonnateurComponent } from '../list/donnateur.component';
import { DonnateurDetailComponent } from '../detail/donnateur-detail.component';
import { DonnateurUpdateComponent } from '../update/donnateur-update.component';
import { DonnateurRoutingResolveService } from './donnateur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const donnateurRoute: Routes = [
  {
    path: '',
    component: DonnateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonnateurDetailComponent,
    resolve: {
      donnateur: DonnateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonnateurUpdateComponent,
    resolve: {
      donnateur: DonnateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonnateurUpdateComponent,
    resolve: {
      donnateur: DonnateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donnateurRoute)],
  exports: [RouterModule],
})
export class DonnateurRoutingModule {}

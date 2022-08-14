import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CreateurAfricainComponent } from '../list/createur-africain.component';
import { CreateurAfricainDetailComponent } from '../detail/createur-africain-detail.component';
import { CreateurAfricainUpdateComponent } from '../update/createur-africain-update.component';
import { CreateurAfricainRoutingResolveService } from './createur-africain-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const createurAfricainRoute: Routes = [
  {
    path: '',
    component: CreateurAfricainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CreateurAfricainDetailComponent,
    resolve: {
      createurAfricain: CreateurAfricainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CreateurAfricainUpdateComponent,
    resolve: {
      createurAfricain: CreateurAfricainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CreateurAfricainUpdateComponent,
    resolve: {
      createurAfricain: CreateurAfricainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(createurAfricainRoute)],
  exports: [RouterModule],
})
export class CreateurAfricainRoutingModule {}

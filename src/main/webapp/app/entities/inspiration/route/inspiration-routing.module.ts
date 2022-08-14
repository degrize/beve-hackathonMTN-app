import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InspirationComponent } from '../list/inspiration.component';
import { InspirationDetailComponent } from '../detail/inspiration-detail.component';
import { InspirationUpdateComponent } from '../update/inspiration-update.component';
import { InspirationRoutingResolveService } from './inspiration-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const inspirationRoute: Routes = [
  {
    path: '',
    component: InspirationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InspirationDetailComponent,
    resolve: {
      inspiration: InspirationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InspirationUpdateComponent,
    resolve: {
      inspiration: InspirationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InspirationUpdateComponent,
    resolve: {
      inspiration: InspirationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inspirationRoute)],
  exports: [RouterModule],
})
export class InspirationRoutingModule {}

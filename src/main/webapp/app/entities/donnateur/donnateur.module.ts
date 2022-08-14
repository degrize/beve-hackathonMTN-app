import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonnateurComponent } from './list/donnateur.component';
import { DonnateurDetailComponent } from './detail/donnateur-detail.component';
import { DonnateurUpdateComponent } from './update/donnateur-update.component';
import { DonnateurDeleteDialogComponent } from './delete/donnateur-delete-dialog.component';
import { DonnateurRoutingModule } from './route/donnateur-routing.module';

@NgModule({
  imports: [SharedModule, DonnateurRoutingModule],
  declarations: [DonnateurComponent, DonnateurDetailComponent, DonnateurUpdateComponent, DonnateurDeleteDialogComponent],
})
export class DonnateurModule {}

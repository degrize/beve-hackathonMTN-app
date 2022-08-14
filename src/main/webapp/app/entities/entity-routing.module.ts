import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'donnateur',
        data: { pageTitle: 'beveApp.donnateur.home.title' },
        loadChildren: () => import('./donnateur/donnateur.module').then(m => m.DonnateurModule),
      },
      {
        path: 'don',
        data: { pageTitle: 'beveApp.don.home.title' },
        loadChildren: () => import('./don/don.module').then(m => m.DonModule),
      },
      {
        path: 'inspiration',
        data: { pageTitle: 'beveApp.inspiration.home.title' },
        loadChildren: () => import('./inspiration/inspiration.module').then(m => m.InspirationModule),
      },
      {
        path: 'createur-africain',
        data: { pageTitle: 'beveApp.createurAfricain.home.title' },
        loadChildren: () => import('./createur-africain/createur-africain.module').then(m => m.CreateurAfricainModule),
      },
      {
        path: 'nature-createur',
        data: { pageTitle: 'beveApp.natureCreateur.home.title' },
        loadChildren: () => import('./nature-createur/nature-createur.module').then(m => m.NatureCreateurModule),
      },
      {
        path: 'transaction',
        data: { pageTitle: 'beveApp.transaction.home.title' },
        loadChildren: () => import('./transaction/transaction.module').then(m => m.TransactionModule),
      },
      {
        path: 'souscription',
        data: { pageTitle: 'beveApp.souscription.home.title' },
        loadChildren: () => import('./souscription/souscription.module').then(m => m.SouscriptionModule),
      },
      {
        path: 'reseaux-sociaux',
        data: { pageTitle: 'beveApp.reseauxSociaux.home.title' },
        loadChildren: () => import('./reseaux-sociaux/reseaux-sociaux.module').then(m => m.ReseauxSociauxModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ApiGroupComponent} from './api-group.component';

const routes: Routes = [
  {
    path: '',
    component: ApiGroupComponent,
    data: {
      title: 'api-group'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApiGroupRoutingModule {
}

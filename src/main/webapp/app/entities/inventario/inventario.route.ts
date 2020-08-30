import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Inventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { InventarioComponent } from './inventario.component';
import { InventarioDetailComponent } from './inventario-detail.component';
import { InventarioUpdateComponent } from './inventario-update.component';
import { IInventario } from 'app/shared/model/inventario.model';

@Injectable({ providedIn: 'root' })
export class InventarioResolve implements Resolve<IInventario> {
  constructor(private service: InventarioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventario> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((inventario: HttpResponse<Inventario>) => inventario.body));
    }
    return of(new Inventario());
  }
}

export const inventarioRoute: Routes = [
  {
    path: '',
    component: InventarioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventarioDetailComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventarioUpdateComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventarioUpdateComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Talla } from 'app/shared/model/talla.model';
import { TallaService } from './talla.service';
import { TallaComponent } from './talla.component';
import { TallaDetailComponent } from './talla-detail.component';
import { TallaUpdateComponent } from './talla-update.component';
import { ITalla } from 'app/shared/model/talla.model';

@Injectable({ providedIn: 'root' })
export class TallaResolve implements Resolve<ITalla> {
  constructor(private service: TallaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITalla> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((talla: HttpResponse<Talla>) => talla.body));
    }
    return of(new Talla());
  }
}

export const tallaRoute: Routes = [
  {
    path: '',
    component: TallaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.talla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TallaDetailComponent,
    resolve: {
      talla: TallaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.talla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TallaUpdateComponent,
    resolve: {
      talla: TallaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.talla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TallaUpdateComponent,
    resolve: {
      talla: TallaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.talla.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

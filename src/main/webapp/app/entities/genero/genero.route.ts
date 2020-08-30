import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Genero } from 'app/shared/model/genero.model';
import { GeneroService } from './genero.service';
import { GeneroComponent } from './genero.component';
import { GeneroDetailComponent } from './genero-detail.component';
import { GeneroUpdateComponent } from './genero-update.component';
import { IGenero } from 'app/shared/model/genero.model';

@Injectable({ providedIn: 'root' })
export class GeneroResolve implements Resolve<IGenero> {
  constructor(private service: GeneroService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGenero> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((genero: HttpResponse<Genero>) => genero.body));
    }
    return of(new Genero());
  }
}

export const generoRoute: Routes = [
  {
    path: '',
    component: GeneroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.genero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GeneroDetailComponent,
    resolve: {
      genero: GeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.genero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GeneroUpdateComponent,
    resolve: {
      genero: GeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.genero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GeneroUpdateComponent,
    resolve: {
      genero: GeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'norforkApp.genero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

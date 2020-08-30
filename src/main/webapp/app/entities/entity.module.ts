import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'genero',
        loadChildren: () => import('./genero/genero.module').then(m => m.NorforkGeneroModule)
      },
      {
        path: 'talla',
        loadChildren: () => import('./talla/talla.module').then(m => m.NorforkTallaModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.NorforkProductoModule)
      },
      {
        path: 'inventario',
        loadChildren: () => import('./inventario/inventario.module').then(m => m.NorforkInventarioModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NorforkEntityModule {}

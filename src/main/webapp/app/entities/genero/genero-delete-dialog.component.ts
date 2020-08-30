import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGenero } from 'app/shared/model/genero.model';
import { GeneroService } from './genero.service';

@Component({
  templateUrl: './genero-delete-dialog.component.html'
})
export class GeneroDeleteDialogComponent {
  genero: IGenero;

  constructor(protected generoService: GeneroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.generoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'generoListModification',
        content: 'Deleted an genero'
      });
      this.activeModal.dismiss(true);
    });
  }
}

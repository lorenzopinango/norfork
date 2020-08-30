import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITalla } from 'app/shared/model/talla.model';
import { TallaService } from './talla.service';

@Component({
  templateUrl: './talla-delete-dialog.component.html'
})
export class TallaDeleteDialogComponent {
  talla: ITalla;

  constructor(protected tallaService: TallaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tallaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tallaListModification',
        content: 'Deleted an talla'
      });
      this.activeModal.dismiss(true);
    });
  }
}

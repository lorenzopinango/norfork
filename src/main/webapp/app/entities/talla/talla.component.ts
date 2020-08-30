import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITalla } from 'app/shared/model/talla.model';
import { TallaService } from './talla.service';
import { TallaDeleteDialogComponent } from './talla-delete-dialog.component';

@Component({
  selector: 'jhi-talla',
  templateUrl: './talla.component.html'
})
export class TallaComponent implements OnInit, OnDestroy {
  tallas: ITalla[];
  eventSubscriber: Subscription;

  constructor(protected tallaService: TallaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.tallaService.query().subscribe((res: HttpResponse<ITalla[]>) => {
      this.tallas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTallas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITalla) {
    return item.id;
  }

  registerChangeInTallas() {
    this.eventSubscriber = this.eventManager.subscribe('tallaListModification', () => this.loadAll());
  }

  delete(talla: ITalla) {
    const modalRef = this.modalService.open(TallaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.talla = talla;
  }
}

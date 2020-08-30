import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NorforkTestModule } from '../../../test.module';
import { TallaDeleteDialogComponent } from 'app/entities/talla/talla-delete-dialog.component';
import { TallaService } from 'app/entities/talla/talla.service';

describe('Component Tests', () => {
  describe('Talla Management Delete Component', () => {
    let comp: TallaDeleteDialogComponent;
    let fixture: ComponentFixture<TallaDeleteDialogComponent>;
    let service: TallaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NorforkTestModule],
        declarations: [TallaDeleteDialogComponent]
      })
        .overrideTemplate(TallaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TallaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TallaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

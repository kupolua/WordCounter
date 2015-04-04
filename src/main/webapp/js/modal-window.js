/*
 * SimpleModal OSX Style Modal Dialog
 * http://simplemodal.com
 *
 * Copyright (c) 2013 Eric Martin - http://ericmmartin.com
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 */

jQuery(function ($) {
	var OSX = {
		container: null,
		init: function () {
			$("input.osx, a.osx").click(function (e) {
				e.preventDefault();	

				$("#osx-modal-content").modal({
					overlayId: 'osx-overlay',
					containerId: 'osx-container',
					closeHTML: null,
					minHeight: 80,
					opacity: 65, 
					position: ['0',],
					overlayClose: true,
					onOpen: OSX.open,
					onClose: OSX.close
				});
			});
		},
		open: function (d) {
			var self = this;
			self.container = d.container[0];
			d.overlay.fadeIn('slow', function () {
				$("#osx-modal-content", self.container).show();
				var title = $("#osx-modal-title", self.container);
				title.show();
				d.container.slideDown('slow', function () {
					setTimeout(function () {
						var h = $("#osx-modal-data", self.container).height()
							+ title.height()
							+ 20; // padding
						d.container.animate(
							{height: h}, 
							200,
							function () {
								$("div.close", self.container).show();
								$("#osx-modal-data", self.container).show();
							}
						);
					}, 300);
				});
			})
		},
		close: function (d) {
			var self = this; // this = SimpleModal object
			d.container.animate(
				{top:"-" + (d.container.height() + 20)},
				500,
				function () {
					self.close(); // or $.modal.close();
				}
			);
		}
	};

	OSX.init();

});

jQuery(function ($) {
    var OSXwordCloud = {
        container: null,
        init: function () {
            $("input.osx-wordCloud, a.osx-wordCloud").click(function (e) {
                e.preventDefault();

                $("#osx-modal-content-wordCloud").modal({
                    overlayId: 'osx-overlay-wordCloud',
                    containerId: 'osx-container-wordCloud',
                    closeHTML: null,
                    minHeight: 80,
                    opacity: 65,
                    position: ['0',],
                    overlayClose: true,
                    onOpen: OSXwordCloud.open,
                    onClose: OSXwordCloud.close
                });
            });
        },
        open: function (d) {
            var self = this;
            self.container = d.container[0];
            d.overlay.fadeIn('slow', function () {
                $("#osx-modal-content-wordCloud", self.container).show();
                var title = $("#osx-modal-title-wordCloud", self.container);
                title.show();
                d.container.slideDown('slow', function () {
                    setTimeout(function () {
                        var h = $("#osx-modal-data-wordCloud", self.container).height()
                            + title.height()
                            + 20; // padding
                        d.container.animate(
                            {height: h},
                            200,
                            function () {
                                $("div.close", self.container).show();
                                $("#osx-modal-data-wordCloud", self.container).show();
                            }
                        );
                    }, 300);
                });
            })
        },
        close: function (d) {
            var self = this; // this = SimpleModal object
            d.container.animate(
                {top:"-" + (d.container.height() + 20)},
                500,
                function () {
                    $("#isWordCloudModalClosed").show();
                    self.close(); // or $.modal.close();
                }
            );
        }
    };

    OSXwordCloud.init();

});
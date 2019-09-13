# base recipe: meta-oe/recipes-support/xdg-user-dirs/xdg-user-dirs_0.17.bb
# base branch: warrior
# base commit: 81ccb9735a4ea263caf950c68648ce1d50db34ad
DESCRIPTION = "xdg-user-dirs is a tool to help manage user directories like the desktop folder and the music folder"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-package
require recipes-debian/sources/xdg-user-dirs.inc

inherit autotools gettext

EXTRA_OECONF = "--disable-documentation"

CONFFILES_${PN} += " \
    ${sysconfdir}/xdg/user-dirs.conf \
    ${sysconfdir}/xdg/user-dirs.defaults \
"

# base recipe : meta/recipes-connectivity/connman/connman_1.36.bb
# base branch : warrior
# base commit : dc804276ef79bee3818deb1a6586ba65cc7b4a3a

require ${COREBASE}/meta/recipes-connectivity/connman/connman.inc
inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/connman/${PN}"
SRC_URI  += "\
    file://0001-plugin.h-Change-visibility-to-default-for-debug-symb.patch \
    file://0001-connman.service-stop-systemd-resolved-when-we-use-co.patch \
    file://connman \
    file://no-version-scripts.patch \
    file://0001-Fix-various-issues-which-cause-problems-under-musl.patch \
    file://0002-resolve-musl-does-not-implement-res_ninit.patch \
"

RRECOMMENDS_${PN} = "connman-conf"

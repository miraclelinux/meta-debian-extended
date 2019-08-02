# base recipe:
# base branch: warrior
# base commit:

SUMMARY = "Assistive Technology Service Provider Interface (dbus core)"
HOMEPAGE = "https://wiki.linuxfoundation.org/accessibility/d-bus"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e9f288ba982d60518f375b5898283886"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

X11DEPENDS = "virtual/libx11 libxi libxtst"

DEPENDS = "dbus glib-2.0"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '${X11DEPENDS}', '', d)}"

inherit meson gtk-doc gettext systemd pkgconfig upstream-version-is-even gobject-introspection

EXTRA_OEMESON = "\
    -Dsystemd_user_dir=${systemd_user_unitdir} \
    -Ddbus_daemon=${bindir}/dbus-daemon \
"

GTKDOC_ENABLE_FLAG = "-Denable_docs=true"
GTKDOC_DISABLE_FLAG = "-Denable_docs=false"

EXTRA_OEMESON_append_class-target = "\
    ${@bb.utils.contains('GTKDOC_ENABLED', 'True', '${GTKDOC_ENABLE_FLAG}', '${GTKDOC_DISABLE_FLAG}', d)}\
"

GIR_MESON_OPTION = 'enable-introspection'
GIR_MESON_ENABLE_FLAG = 'yes'
GIR_MESON_DISABLE_FLAG = 'no'

FILES_${PN} += "\
    ${datadir}/dbus-1/services/*.service \
    ${datadir}/dbus-1/accessibility-services/*.service \
    ${datadir}/defaults/at-spi2 \
    ${systemd_user_unitdir}/at-spi-dbus-bus.service \
"
BBCLASSEXTEND = "native nativesdk"

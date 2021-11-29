#base recipe: meta-openembedded/meta-oe/recipes-extended/polkit/polkit_0.104.bb
#base branch: dylan
#base commif: 4e3362f9c2b5540231011930b91d2cf56e487ff7

SUMMARY = "PolicyKit Authorization Framework"
DESCRIPTION = "The polkit package is an application-level toolkit for defining and handling the policy that allows unprivileged processes to speak to privileged processes."
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/polkit"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=155db86cdbafa7532b41f390409283eb \
                    file://src/polkit/polkit.h;beginline=1;endline=20;md5=0a8630b0133176d0504c87a0ded39db4"

DEPENDS = "expat glib-2.0 intltool-native"

inherit autotools gtk-doc pkgconfig useradd systemd distro_features_check gobject-introspection

inherit debian-package
require recipes-debian/sources/policykit-1.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/polkit-${PV}"

REQUIRED_DISTRO_FEATURES = "polkit"

PACKAGECONFIG = "${@bb.utils.filter('DISTRO_FEATURES', 'pam', d)} \
                "

PACKAGECONFIG[pam] = "--with-authfw=pam,--with-authfw=shadow,libpam,libpam"


PAM_SRC_URI = "file://polkit-1_pam.patch"
SRC_URI += " \
           ${@bb.utils.contains('DISTRO_FEATURES', 'pam', '${PAM_SRC_URI}', '', d)} \
           file://0001-exec-path-change-polkit.service.patch \
"

EXTRA_OECONF = "--with-os-type=moblin --disable-man-pages --disable-introspection --enable-systemd"


do_install_append() {
    rm -f ${D}${libdir}/${BPN}-1/extensions/*.a
    install -d ${D}${base_libdir}/systemd/system
    install -m 644 ${S}/debian/polkit.service ${D}${base_libdir}/systemd/system
}

FILES_${PN}_append = " \
                ${libdir}/${BPN}-1 \
                ${nonarch_libdir}/${BPN}-1 \
                ${datadir}/dbus-1 \
                ${datadir}/${BPN}-1 \
                ${datadir}/gettext\
                ${base_libdir} \
"
FILES_${PN}_append = " \
"
FILES_${PN}-dbg += "${libdir}/${BPN}-1/extensions/.debug/*.so"
FILES_${PN}-dev += "${libdir}/${BPN}-1/extensions/*.la "

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --no-create-home --user-group --home-dir ${sysconfdir}/${BPN}-1 polkitd"

RDEPENDS_${PN} += "dbus "
SYSTEMD_SERVICE_${PN} = "${BPN}.service"
SYSTEMD_AUTO_ENABLE = "disable"

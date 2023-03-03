#base recipe: meta-cgl/meta-cgl-common/recipes-cgl/cluster-resource-agents/resource-agents_4.5.0.bb
#base branch: dunfell
#base commit: b8529657086ba4d308ee8cc99b720aaedaa3ae18

SUMMARY = "OCF resource agents for use by compatible cluster managers"
DESCRIPTION = "A set of scripts to interface with several services \
to operate in a High Availability environment for both Pacemaker and \
rgmanager service managers."
HOMEPAGE = "http://sources.redhat.com/cluster/wiki/"

LICENSE = "GPLv2+ & LGPLv2+ & GPLv3"
LICENSE_${PN} = "GPLv2+ & LGPLv2+"
LICENSE_${PN}-dev = "GPLv2+ & LGPLv2+"
LICENSE_${PN}-staticdev = "GPLv2+ & LGPLv2+"
LICENSE_${PN}-dbg = "GPLv2+ & LGPLv2+"
LICENSE_${PN}-doc = "GPLv2+ & LGPLv2+"
LICENSE_${PN}-extra = "GPLv3"
LICENSE_${PN}-extra-dbg = "GPLv3"

inherit debian-package
require recipes-debian/sources/resource-agents.inc

SRC_URI += " \
           file://01-disable-doc-build.patch \
           file://02-set-OCF_ROOT_DIR-to-libdir-ocf.patch \
           file://03-fix-header-defs-lookup.patch \
           file://fix-install-sh-not-found.patch \
           file://resource-agents.rtupdate \
          "

LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://COPYING.LGPL;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "cluster-glue"

# There are many tools and scripts that need bash and perl.
# lvm.sh requires: lvm2
# ip.sh requires: ethtool iproute2 iputils-arping
# fs.sh requires: e2fsprogs-e2fsck util-linux quota
# netfs.sh requires: procps util-linux nfs-utils
RDEPENDS_${PN} += "bash perl lvm2 \
    ethtool iproute2 iputils-arping \
    e2fsprogs-e2fsck util-linux quota \
    procps nfs-utils \
"

inherit autotools systemd pkgconfig

CACHED_CONFIGUREVARS += " \
    ac_cv_path_GREP=/bin/grep \
    ac_cv_path_TEST=/usr/bin/test \
    ac_cv_path_SSH=/usr/bin/ssh \
    ac_cv_path_BASH_SHELL=/bin/bash \
    ac_cv_path_PYTHON="/usr/bin/env python3" \
"

EXTRA_OECONF += "--disable-fatal-warnings \
                 --with-rsctmpdir=/run/resource-agents \
                 --libexecdir=${libdir}"

do_install_append() {
    rm -rf "${D}${localstatedir}/run"
    rmdir --ignore-fail-on-non-empty "${D}${localstatedir}"

    install -d ${D}${datadir}/python3/runtime.d
    install -m 0755 ${WORKDIR}/resource-agents.rtupdate ${D}${datadir}/python3/runtime.d

    rm ${D}${libdir}/ocf/resource.d/redhat
    ln -sf ../../../share/cluster ${D}${libdir}/ocf/resource.d/redhat
}

# tickle_tcp is published under GPLv3, we just split it into ${PN}-extra,
# and it's required by portblock, so move portblock into ${PN}-extra together.
PACKAGES_prepend  = "${PN}-extra ${PN}-extra-dbg"
NOAUTOPACKAGEDEBUG = "1"
FILES_${PN}-extra = "${libdir}/heartbeat/tickle_tcp \
                     ${libdir}/ocf/resource.d/heartbeat/portblock \
                     ${datadir}/resource-agents/ocft/configs/portblock \
                    "
FILES_${PN}-extra-dbg = "${libdir}/heartbeat/.debug/tickle_tcp"

FILES_${PN} += "${datadir}/cluster/* \
                ${libdir}/heartbeat/* \
                ${libdir}/ocf/resource.d/heartbeat/ \
                ${libdir}/ocf/lib/heartbeat/* \
                ${libdir}/ocf/resource.d/redhat \
                ${nonarch_libdir}/tmpfiles.d \
                ${systemd_unitdir}/system \
                /run/resource-agents \
                ${datadir}/python3/runtime.d/resource-agents.rtupdate \
                "

FILES_${PN}-dbg += "${libdir}/ocf/resource.d/heartbeat/.debug \
                    ${sbindir}/.debug \
                    ${libdir}/heartbeat/.debug "

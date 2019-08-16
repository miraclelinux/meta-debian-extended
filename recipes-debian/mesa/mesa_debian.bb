# base recipe: meta/recipes-graphics/mesa/mesa_19.0.1.bb
# base branch: warrior
# base commit: 259e9a11e1a62b0dc192d9c114f05cd45b02cae1

require ${COREBASE}/meta/recipes-graphics/mesa/mesa.inc

inherit debian-package
require recipes-debian/sources/${BPN}.inc

DEBIAN_PATCH_TYPE = "quilt"

FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/mesa/files"
SRC_URI += " \
           file://0001-Simplify-wayland-scanner-lookup.patch \
           file://0002-winsys-svga-drm-Include-sys-types.h.patch \
           file://0003-Properly-get-LLVM-version-when-using-LLVM-Git-releas.patch \
           file://0004-use-PKG_CHECK_VAR-for-defining-WAYLAND_PROTOCOLS_DAT.patch \
           file://Fix-mising-NULL-compile-failure.patch \
"

EXTRA_OECONF = "--enable-shared-glapi \
                --disable-opencl \
                --enable-glx-read-only-text \
                PYTHON2=python2 \
                --with-llvm-prefix=${STAGING_LIBDIR}/llvm${MESA_LLVM_RELEASE} \
                --with-platforms='${PLATFORMS}' \
"

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}

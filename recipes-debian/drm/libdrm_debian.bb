# base recipe: meta/recipes-graphics/drm/libdrm_2.4.97.bb
# base branch: warrior
# base commit: f4a270a519a14652b7c13a151068b4ef255710e7

SUMMARY = "Userspace interface to the kernel DRM services"
DESCRIPTION = "The runtime library for accessing the kernel DRM services.  DRM \
stands for \"Direct Rendering Manager\", which is the kernel portion of the \
\"Direct Rendering Infrastructure\" (DRI).  DRI is required for many hardware \
accelerated OpenGL drivers."
HOMEPAGE = "http://dri.freedesktop.org"
SECTION = "x11/base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://xf86drm.c;beginline=9;endline=32;md5=c8a3b961af7667c530816761e949dc71"
PROVIDES = "drm"
DEPENDS = "libpthread-stubs"

inherit debian-package
require recipes-debian/sources/libdrm.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/drm/libdrm"
DEBIAN_PATCH_TYPE = "quilt"

SRC_URI += "file://musl-ioctl.patch"

inherit meson pkgconfig manpages

PACKAGECONFIG ??= "libkms intel radeon amdgpu nouveau vmwgfx omap freedreno vc4 etnaviv install-test-programs"
PACKAGECONFIG[libkms] = "-Dlibkms=true,-Dlibkms=false"
PACKAGECONFIG[intel] = "-Dintel=true,-Dintel=false,libpciaccess"
PACKAGECONFIG[radeon] = "-Dradeon=true,-Dradeon=false"
PACKAGECONFIG[amdgpu] = "-Damdgpu=true,-Damdgpu=false"
PACKAGECONFIG[nouveau] = "-Dnouveau=true,-Dnouveau=false"
PACKAGECONFIG[vmwgfx] = "-Dvmwgfx=true,-Dvmwgfx=false"
PACKAGECONFIG[omap] = "-Domap=true,-Domap=false"
PACKAGECONFIG[exynos] = "-Dexynos=true,-Dexynos=false"
PACKAGECONFIG[freedreno] = "-Dfreedreno=true,-Dfreedreno=false"
PACKAGECONFIG[tegra] = "-Dtegra=true,-Dtegra=false"
PACKAGECONFIG[vc4] = "-Dvc4=true,-Dvc4=false"
PACKAGECONFIG[etnaviv] = "-Detnaviv=true,-Detnaviv=false"
PACKAGECONFIG[freedreno-kgsl] = "-Dfreedreno-kgsl=true,-Dfreedreno-kgsl=false"
PACKAGECONFIG[valgrind] = "-Dvalgrind=true,-Dvalgrind=false,valgrind"
PACKAGECONFIG[install-test-programs] = "-Dinstall-test-programs=true,-Dinstall-test-programs=false"
PACKAGECONFIG[cairo-tests] = "-Dcairo-tests=true,-Dcairo-tests=false"
PACKAGECONFIG[udev] = "-Dudev=true,-Dudev=false,udev"
PACKAGECONFIG[manpages] = "-Dman-pages=true,-Dman-pages=false,libxslt-native xmlto-native"

ALLOW_EMPTY_${PN}-drivers = "1"
PACKAGES =+ "${PN}-tests ${PN}-drivers ${PN}-radeon ${PN}-nouveau ${PN}-omap \
             ${PN}-intel ${PN}-exynos ${PN}-kms ${PN}-freedreno ${PN}-amdgpu \
             ${PN}-etnaviv"

RRECOMMENDS_${PN}-drivers = "${PN}-radeon ${PN}-nouveau ${PN}-omap ${PN}-intel \
                             ${PN}-exynos ${PN}-freedreno ${PN}-amdgpu \
                             ${PN}-etnaviv"

FILES_${PN}-tests = "${bindir}/*"
FILES_${PN}-radeon = "${libdir}/libdrm_radeon.so.*"
FILES_${PN}-nouveau = "${libdir}/libdrm_nouveau.so.*"
FILES_${PN}-omap = "${libdir}/libdrm_omap.so.*"
FILES_${PN}-intel = "${libdir}/libdrm_intel.so.*"
FILES_${PN}-exynos = "${libdir}/libdrm_exynos.so.*"
FILES_${PN}-kms = "${libdir}/libkms*.so.*"
FILES_${PN}-freedreno = "${libdir}/libdrm_freedreno.so.*"
FILES_${PN}-amdgpu = "${libdir}/libdrm_amdgpu.so.*"
FILES_${PN}-etnaviv = "${libdir}/libdrm_etnaviv.so.*"

BBCLASSEXTEND = "native nativesdk"

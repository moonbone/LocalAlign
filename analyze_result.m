function [ score, mismatch,gap,ratio ] = analyze_result( scores,r,g,m )
%ANALYZE_RESULT Summary of this function goes here
%   Detailed explanation goes here

if size(scores,3) > 1 
    f = factor(size(scores,1));
    if size(f,2) > 1
        rows = max(f);
        rowPos = find(f == rows,1);
        f(rowPos) = 1;
        cols = 2 * prod(f);
    else
        rows = 10;
        cols = 2*ceil(f/10);
    end
    for i=1:size(scores,1)
        s = reshape(scores(i,:,:),size(g,2),size(r,2));
        figure(1)
        %subplot(size(scores,1),2,2*i - 1)
        subplot(rows,cols,2*i - 1)
        mesh(g,r,s)
        xlabel('gap score')
        ylabel('mismatch score')
        zlabel('BRAlibase based alignment quality')
        title(['probs score ratio  = ' num2str(m(i))])
        [GX,GY] = gradient(s);
        %subplot(size(scores,1),2,2*i)
        subplot(rows,cols,2*i)
        contour(g,r,s)
        hold on
        quiver(g,r,GX,GY)
        xlabel('gap score')
        ylabel('mismatch score')
        title(['probs score ratio = ' num2str(m(i))])
        legend('BRAlibase based alignment quality')
        hold off
    end
    score = max(scores(:));
    [M,R] = find(scores == max(scores(:)));
    ratio = m(M);
    S = size(scores);
    [R,C] = ind2sub(S(2:3),R);
    gap = g(C);
    mismatch = r(R);
else
    figure(1)
    subplot(1,2,1)
    mesh(g,r,scores)
    xlabel('gap score')
    ylabel('mismatch score')
    zlabel('BRAlibase based alignment quality')
    title(['match score = ' num2str(m)])
    [GX,GY] = gradient(scores);
    subplot(1,2,2)
    contour(g,r,scores)
    hold on
    quiver(g,r,GX,GY)
    xlabel('gap score')
    ylabel('mismatch score')
    title(['match score = ' num2str(m)])
    legend('BRAlibase based alignment quality')
    hold off
    score = max(scores(:));
    [R,C] = find(scores == max(scores(:)));
    gap = g(C);
    mismatch = r(R);
    ratio = NaN;
end




end

